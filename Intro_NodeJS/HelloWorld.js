const http = require('http');
const mod = require('./HWModule');

http.createServer(function(req, res) {
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.write('<h1>' + mod.helloWorld() + '</h1>');
    res.write('That message was brought to you by a custom module');
    res.end();
}).listen(8080);