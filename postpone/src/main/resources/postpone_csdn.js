var page = require('webpage').create(),
    system = require('system'),
    address, output, size;
if (system.args.length < 3 || system.args.length > 5) {
    console.log('Usage: rasterize.js URL filename');
    phantom.exit(1);
} else {
    address = system.args[1];
    output = system.args[2];
    page.viewportSize = { 'width': 1920, 'height': 1080 };
    page.open(address, function (status) {
        // 通过在页面上执行脚本获取页面的渲染高度
        var bb = page.evaluate(function () {
            return document.getElementsByTagName('html')[0].getBoundingClientRect();
        });
        // 按照实际页面的高度，设定渲染的宽高
        page.clipRect = {
            top:    20,
            left:   400,
            width:  1000,
            height: 800
        };
        // 预留一定的渲染时间
        window.setTimeout(function () {
            page.render(output);
            page.close();
            console.log('render ok');
        }, 10000);
    });
}