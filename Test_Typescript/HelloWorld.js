function hello() {
    return 'Hello World!';
}
document.getElementById('blah').innerHTML = hello();
var myName = ['Chris', 'World', 'Blah', 'Test'];
function helloTwo(name) {
    return 'Hello ' + name + "!";
}
function onClickGreeting() {
    var rand = Math.floor(Math.random() * 4);
    document.getElementById('blah').innerHTML = helloTwo(myName[rand]);
}
