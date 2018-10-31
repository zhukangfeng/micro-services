var http = require('http');
var httpProxy = require('http-proxy');

var PORT = 10080;

var proxy = httpProxy.createProxyServer();
proxy.on('error', function (err, req, res) {
    res.end('err');
})

var app = http.createServer(function (req, res) {
    proxy.web(req, res, {
        target: 'http://debian-02:10080'
    });
});

app.listen(PORT, function () {
    console.log('server is running at %d', PORT);
})