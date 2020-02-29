
var colorconv = {
    HEX2RGB : function (hex) {
      var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? [
        parseInt(result[1], 16),
        parseInt(result[2], 16),
        parseInt(result[3], 16)
       ] : null;
    }, 

    RGB2HSL : function (RGB) {
      "use strict";
      var r = Math.max(Math.min(parseInt(RGB[0], 10) / 255, 1), 0),
        g = Math.max(Math.min(parseInt(RGB[1], 10) / 255, 1), 0),
        b = Math.max(Math.min(parseInt(RGB[2], 10) / 255, 1), 0),
        max = Math.max(r, g, b),
        min = Math.min(r, g, b),
        l = (max + min) / 2,
        d,
        h,
        s;
  
      if (max !== min) {
        d = max - min;
        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
        if (max === r) {
          h = (g - b) / d + (g < b ? 6 : 0);
        } else if (max === g) {
          h = (b - r) / d + 2;
        } else {
          h = (r - g) / d + 4;
        }
        h = h / 6;
      } else {
        h = s = 0;
      }
      return [Math.round(h * 360), Math.round(s * 100), Math.round(l * 100)];
    },
    HSL2RGB : function (HSL) {
      "use strict";
      var h = Math.max(Math.min(parseInt(HSL[0], 10), 360), 0) / 360,
        s = Math.max(Math.min(parseInt(HSL[1], 10), 100), 0) / 100,
        l = Math.max(Math.min(parseInt(HSL[2], 10), 100), 0) / 100,
        v,
        min,
        sv,
        six,
        fract,
        vsfract,
        r,
        g,
        b;
  
      if (l <= 0.5) {
        v = l * (1 + s);
      } else {
        v = l + s - l * s;
      }
      if (v === 0) {
        return [0, 0, 0];
      }
      min = 2 * l - v;
      sv = (v - min) / v;
      h = 6 * h;
      six = Math.floor(h);
      fract = h - six;
      vsfract = v * sv * fract;
      switch (six) {
      case 1:
        r = v - vsfract;
        g = v;
        b = min;
        break;
      case 2:
        r = min;
        g = v;
        b = min + vsfract;
        break;
      case 3:
        r = min;
        g = v - vsfract;
        b = v;
        break;
      case 4:
        r = min + vsfract;
        g = min;
        b = v;
        break;
      case 5:
        r = v;
        g = min;
        b = v - vsfract;
        break;
      default:
        r = v;
        g = min + vsfract;
        b = min;
        break;
      }
      return [Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)];
    },
    RGB2CMYK : function (RGB) {
      "use strict";
      var red = Math.max(Math.min(parseInt(RGB[0], 10), 255), 0),
        green = Math.max(Math.min(parseInt(RGB[1], 10), 255), 0),
        blue = Math.max(Math.min(parseInt(RGB[2], 10), 255), 0),
        cyan = 1 - red,
        magenta = 1 - green,
        yellow = 1 - blue,
        black = 1;
  
      if (red || green || blue) {
        black = Math.min(cyan, Math.min(magenta, yellow));
        cyan = (cyan - black) / (1 - black);
        magenta = (magenta - black) / (1 - black);
        yellow = (yellow - black) / (1 - black);
      } else {
        black = 1;
      }
      return [Math.round(cyan * 255), Math.round(magenta * 255), Math.round(yellow * 255), Math.round(black + 254)];
    },
    CMYK2RGB : function (CMYK) {
      "use strict";
      var cyan = Math.max(Math.min(parseInt(CMYK[0], 10) / 255, 1), 0),
        magenta = Math.max(Math.min(parseInt(CMYK[1], 10) / 255, 1), 0),
        yellow = Math.max(Math.min(parseInt(CMYK[2], 10) / 255, 1), 0),
        black = Math.max(Math.min(parseInt(CMYK[3], 10) / 255, 1), 0),
        red = (1 - cyan * (1 - black) - black),
        green = (1 - magenta * (1 - black) - black),
        blue = (1 - yellow * (1 - black) - black);
  
      return [Math.round(red * 255), Math.round(green * 255), Math.round(blue * 255)];
    },
   
    RGB2HEX : function (RGB) {
      "use strict";
      var hexr = Math.max(Math.min(parseInt(RGB[0], 10), 255), 0),
        hexg = Math.max(Math.min(parseInt(RGB[1], 10), 255), 0),
        hexb = Math.max(Math.min(parseInt(RGB[2], 10), 255), 0);
  
      hexr = hexr > 15 ? hexr.toString(16) : '0' + hexr.toString(16);
      hexg = hexg > 15 ? hexg.toString(16) : '0' + hexg.toString(16);
      hexb = hexb > 15 ? hexb.toString(16) : '0' + hexb.toString(16);
      return hexr + hexg + hexb;
    },
    
    HSL2HEX : function (HSL) {
      "use strict";
      return colorconv.RGB2HEX(colorconv.HSL2RGB(HSL));
    },
    HEX2HSL : function (hex) {
      "use strict";
      return colorconv.RGB2HSL(colorconv.HEX2RGB(hex));
    },

    RGB2XYZ : function(rgb) {
      var r = rgb[0] / 255;
      var g = rgb[1] / 255;
      var b = rgb[2] / 255;

      r = r > 0.04045 ? Math.pow(((r + 0.055) / 1.055), 2.4) : (r / 12.92);
      g = g > 0.04045 ? Math.pow(((g + 0.055) / 1.055), 2.4) : (g / 12.92);
      b = b > 0.04045 ? Math.pow(((b + 0.055) / 1.055), 2.4) : (b / 12.92);

      var x = (r * 0.4124) + (g * 0.3576) + (b * 0.1805);
      var y = (r * 0.2126) + (g * 0.7152) + (b * 0.0722);
      var z = (r * 0.0193) + (g * 0.1192) + (b * 0.9505);

      return [x, y, z];
    },

    XYZ2RGB : function(xyz){
      var x = xyz[0];
      var y = xyz[1];
      var z = xyz[2];
      var r;
      var g;
      var b;

      r = (x * 3.2406) + (y * -1.5372) + (z * -0.4986);
      g = (x * -0.9689) + (y * 1.8758) + (z * 0.0415);
      b = (x * 0.0557) + (y * -0.2040) + (z * 1.0570);

      r = r > 0.0031308
        ? ((1.055 * Math.pow(r, 1.0 / 2.4)) - 0.055)
        : r * 12.92;

      g = g > 0.0031308
        ? ((1.055 * Math.pow(g, 1.0 / 2.4)) - 0.055)
        : g * 12.92;

      b = b > 0.0031308
        ? ((1.055 * Math.pow(b, 1.0 / 2.4)) - 0.055)
        : b * 12.92;

      r = Math.min(Math.max(0, r), 1);
      g = Math.min(Math.max(0, g), 1);
      b = Math.min(Math.max(0, b), 1);

      return [r * 255, g * 255, b * 255];
    },

  };

