// -------浏览器对象 BOM-------
// 判读浏览器是否支持CSS属性
/**
 * 告知浏览器支持的指定css属性情况
 * @param {String} key - css属性，是属性的名字，不需要加前缀
 * @returns {String} - 支持的属性情况
 */
function validateCssKey(key) {
  const jsKey = toCamelCase(key); // 有些css属性是连字符号形成
  if (jsKey in document.documentElement.style) {
    return key;
  }
  let validKey = "";
  // 属性名为前缀在js中的形式，属性值是前缀在css中的形式
  // 经尝试，Webkit也可是首字母小写webkit
  const prefixMap = {
    Webkit: "-webkit-",
    Moz: "-moz-",
    ms: "-ms-",
    O: "-o-",
  };
  for (const jsPrefix in prefixMap) {
    const styleKey = toCamelCase(`${jsPrefix}-${jsKey}`);
    if (styleKey in document.documentElement.style) {
      validKey = prefixMap[jsPrefix] + key;
      break;
    }
  }
  return validKey;
}

/**
 * 把有连字符号的字符串转化为驼峰命名法的字符串
 */
function toCamelCase(value) {
  return value.replace(/-(\w)/g, (matched, letter) => {
    return letter.toUpperCase();
  });
}

/**
 * 检查浏览器是否支持某个css属性值（es6版）
 * @param {String} key - 检查的属性值所属的css属性名
 * @param {String} value - 要检查的css属性值（不要带前缀）
 * @returns {String} - 返回浏览器支持的属性值
 */
function valiateCssValue(key, value) {
  const prefix = ["-o-", "-ms-", "-moz-", "-webkit-", ""];
  const prefixValue = prefix.map((item) => {
    return item + value;
  });
  const element = document.createElement("div");
  const eleStyle = element.style;
  // 应用每个前缀的情况，且最后也要应用上没有前缀的情况，看最后浏览器起效的何种情况
  // 这就是最好在prefix里的最后一个元素是''
  prefixValue.forEach((item) => {
    eleStyle[key] = item;
  });
  return eleStyle[key];
}

/**
 * 检查浏览器是否支持某个css属性值
 * @param {String} key - 检查的属性值所属的css属性名
 * @param {String} value - 要检查的css属性值（不要带前缀）
 * @returns {String} - 返回浏览器支持的属性值
 */
function valiateCssValue(key, value) {
  var prefix = ["-o-", "-ms-", "-moz-", "-webkit-", ""];
  var prefixValue = [];
  for (var i = 0; i < prefix.length; i++) {
    prefixValue.push(prefix[i] + value);
  }
  var element = document.createElement("div");
  var eleStyle = element.style;
  for (var j = 0; j < prefixValue.length; j++) {
    eleStyle[key] = prefixValue[j];
  }
  return eleStyle[key];
}

function validCss(key, value) {
  const validCss = validateCssKey(key);
  if (validCss) {
    return validCss;
  }
  return valiateCssValue(key, value);
}

/**
 * 返回当前网页地址⬇
 */ 
// 方案一：location
function currentURL() {
  return window.location.href;
}
currentURL();

// 方案二：a 标签
function currentURL() {
  var el = document.createElement("a");
  el.href = "";
  return el.href;
}
currentURL();

/**
 * 获取滚动条位置⬇
 */ 
function getScrollPosition(el = window) {
  return {
    x: el.pageXOffset !== undefined ? el.pageXOffset : el.scrollLeft,
    y: el.pageYOffset !== undefined ? el.pageYOffset : el.scrollTop,
  };
}

/**
 * 获取url中的参数⬇
 */ 
// 方案一：正则 + reduce
function getURLParameters(url) {
  return url.match(/([^?=&]+)(=([^&]*))/g).reduce((a, v) => (
        (a[v.slice(0, v.indexOf("="))] = v.slice(v.indexOf("=") + 1)), a
      ),{}
    );
}
getURLParameters(location.href);

// 方案二：split + reduce
function getURLParameters(url) {
  return url
    .split("?") //取？分割
    .slice(1) //不要第一部分
    .join() //拼接
    .split("&") //&分割
    .map((v) => v.split("=")) //=分割
    .reduce((s, n) => {
      s[n[0]] = n[1];
      return s;
    }, {});
}
getURLParameters(location.href);
// getURLParameters('')

// 方案三: URLSearchParams

/**
 * 页面跳转，是否记录在 history 中⬇
 */ 
// 方案一：
function redirect(url, asLink = true) {
  asLink ? (window.location.href = url) : window.location.replace(url);
}
// 方案二：
function redirect(url, asLink = true) {
  asLink ? window.location.assign(url) : window.location.replace(url);
}

/**
 * 滚动条回到顶部动画⬇
 */ 
// 方案一： c - c / 8
// c没有定义
function scrollToTop() {
  const scrollTop =
    document.documentElement.scrollTop || document.body.scrollTop;
  if (scrollTop > 0) {
    window.requestAnimationFrame(scrollToTop);
    window.scrollTo(0, c - c / 8);
  } else {
    window.cancelAnimationFrame(scrollToTop);
  }
}
scrollToTop();

// 修正之后
function scrollToTop() {
  const scrollTop =
    document.documentElement.scrollTop || document.body.scrollTop;
  if (scrollTop > 0) {
    window.requestAnimationFrame(scrollToTop);
    window.scrollTo(0, scrollTop - scrollTop / 8);
  } else {
    window.cancelAnimationFrame(scrollToTop);
  }
}
scrollToTop();

/**
 * 复制文本⬇
 */ 
// 方案一：
function copy(str) {
  const el = document.createElement("textarea");
  el.value = str;
  el.setAttribute("readonly", "");
  el.style.position = "absolute";
  el.style.left = "-9999px";
  el.style.top = "-9999px";
  document.body.appendChild(el);
  const selected =
    document.getSelection().rangeCount > 0
      ? document.getSelection().getRangeAt(0)
      : false;
  el.select();
  document.execCommand("copy");
  document.body.removeChild(el);
  if (selected) {
    document.getSelection().removeAllRanges();
    document.getSelection().addRange(selected);
  }
}
// 方案二：cliboard.js

/**
 * 检测设备类型⬇
 */ 
// 方案一： ua
function detectDeviceType() {
  return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
    navigator.userAgent
  )
    ? "Mobile"
    : "Desktop";
}
detectDeviceType();

// 方案二：事件属性
function detectDeviceType() {
  return "ontouchstart" in window || navigator.msMaxTouchPoints
    ? "Mobile"
    : "Desktop";
}
detectDeviceType();
