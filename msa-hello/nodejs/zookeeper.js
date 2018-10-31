var zookeeper = require('node-zookeeper-client');

var CONNECT_STRING = '192.168.56.21:2181';
var OPTIONS = {
    sessionTimeout: 5000
};

var zk = zookeeper.createClient(CONNECT_STRING, OPTIONS);
console.log('begin');
zk.on('connected', function () {
    console.log(zk);
    zk.getChildren('/', function (error, children, stat) {
        console.log('child', children);
        return;
    })
    zk.close();
});
zk.connect()
console.log('end');
