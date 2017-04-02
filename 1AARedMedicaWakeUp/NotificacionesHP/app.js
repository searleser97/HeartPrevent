var port = 8000 // console.log(JSON.stringify(dbdata)); just in case   // var io = require('socket.io').listen(port).sockets
var app = require('express')()
var server = require('http').Server(app)
var io = require('socket.io')(server, {
  pingTimeout: 3000
})
var mysql = require('mysql')
var dbconnection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'n0m3l0',
  database: 'redmedica',
  port: 3306,
  multipleStatements: true
})
var tokensArray = []
var queries
var finalQuery
var POLLING_INTERVAL = 1000
var timeout
var timeout2
server.listen(port, function (err) {
  if (err === undefined) {
    console.log('Server Started Succesfully on port: ' + port)
  } else {
    console.log('Following error has occured: ' + err)
  }
})
dbconnection.connect(function (err) {
  // connected! (unless `err` is set)
  if (err === null) {
    console.log('DB connection status: Connected!')
  } else {
    console.log('DB connection status: Disconnected!')
    console.log('Following error has occured: ' + err)
  }
})
io.on('connection', function (socket) {
  socket.on('Notifications-conn', function (data) {
    dbconnection.query("call verificawsuser('" + data.usrid + "','" + data.token + "')")
      .on('error', function (err) {
        console.log(err)
      })
      .on('result', function (dbdata) {
        if (dbdata.existe === 'si') {
          socket.join(data.token)
          var alreadyExists = false
          tokensArray.forEach(function (sometoken) {
            if (data.token === sometoken) {
              alreadyExists = true
            }
          })
          if (!alreadyExists) {
            tokensArray.push(data.token)
          }
          console.log('User with token: ' + data.token + ' has connected')
          if (tokensArray.length === 1) {
            pollingLoop()
          }
        } else {
          if ((JSON.stringify(dbdata)).indexOf('fieldCount') === -1) {
            console.log('Incorrect token or usrid')
          }
        }
      })
      // --When a user disconnects--------------------------------------------------------
    socket.on('disconnect', function () {
      timeout2 = setTimeout(function () {
        setInactive(data)
      }, 2000)
    })
  })
  var pollingLoop = function () {
    clearTimeout(timeout)
    queries = ''
    tokensArray.forEach(function (tokenfromArray) {
      queries += "call notificacionesnodejs('" + tokenfromArray + "');"
    })
    console.log(queries)
    if (queries !== '' && tokensArray.length !== 0) {
      finalQuery = dbconnection.query(queries)
      var rs = [] // this array will contain the result of our db query
        // set up the query listeners
      finalQuery
        .on('error', function (err) {
          // Handle error, and 'end' event will be emitted after this as well
          console.log('DB error: ' + err)
          updateSockets(err)
        })
        .on('result', function (data) {
          // it fills our array looping on each user row inside the db
          rs.push(data)
        })
        .on('end', function () {
          if (tokensArray.length) {
            timeout = setTimeout(pollingLoop, POLLING_INTERVAL)
            updateSockets({
              rsArray: rs
            })
          }
        })
    }
  }
  var updateSockets = function (data) {
    data.rsArray.forEach(function (dbdata) {
      var notifications = {
        globalnotis: dbdata.globalnotis,
        msjs: dbdata.msjs,
        contacts: dbdata.contacts,
        patients: dbdata.patients,
        friendsUpdates: dbdata.friendsUpdates
      }
      io.in(dbdata.tokenreceived).emit('newNotification', notifications)
    })
  }
  var setInactive = function (data) {
    var tabsopened = io.sockets.adapter.rooms[data.token]
    if (tabsopened === undefined) {
      dbconnection.query("call onlineono('3','" + data.usrid + "')")
      var TokenIndex = tokensArray.indexOf(data.token)
      if (TokenIndex >= 0) {
        tokensArray.splice(TokenIndex, 1)
      }
      console.log('User with token: ' + data.token + ' has disconnected')
      if (tokensArray.length === 0) {
        clearTimeout(timeout)
      }
    }
    clearTimeout(timeout2)
  }
})
