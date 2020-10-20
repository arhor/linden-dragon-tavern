(function (_, Kotlin) {
  'use strict';
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var numberToInt = Kotlin.numberToInt;
  function Authorities() {
    Authorities_instance = this;
  }
  function Authorities$Account() {
    Authorities$Account_instance = this;
    this.VIEW = 'ACCOUNT:VIEW';
    this.EDIT = 'ACCOUNT:EDIT';
  }
  Authorities$Account.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Account',
    interfaces: []
  };
  var Authorities$Account_instance = null;
  function Authorities$Account_getInstance() {
    if (Authorities$Account_instance === null) {
      new Authorities$Account();
    }return Authorities$Account_instance;
  }
  Authorities.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Authorities',
    interfaces: []
  };
  var Authorities_instance = null;
  function Authorities_getInstance() {
    if (Authorities_instance === null) {
      new Authorities();
    }return Authorities_instance;
  }
  function CsrfUtils() {
    CsrfUtils_instance = this;
    this.CSRF_HEADER_NAME = 'x-csrf-token';
    this.CSRF_COOKIE_NAME = 'csrf-token';
    this.SAFE_METHODS = ['GET', 'HEAD', 'OPTIONS', 'TRACE'];
  }
  CsrfUtils.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'CsrfUtils',
    interfaces: []
  };
  var CsrfUtils_instance = null;
  function CsrfUtils_getInstance() {
    if (CsrfUtils_instance === null) {
      new CsrfUtils();
    }return CsrfUtils_instance;
  }
  function Utils() {
    Utils_instance = this;
  }
  Utils.prototype.calcAbilityModifier = function (value) {
    var tmp$;
    var modifier = (numberToInt(value) - 10 | 0) / 2 | 0;
    if (modifier < 0) {
      tmp$ = '-' + modifier;
    } else {
      tmp$ = '+' + modifier;
    }
    return tmp$;
  };
  Utils.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Utils',
    interfaces: []
  };
  var Utils_instance = null;
  function Utils_getInstance() {
    if (Utils_instance === null) {
      new Utils();
    }return Utils_instance;
  }
  Object.defineProperty(Authorities.prototype, 'Account', {
    get: Authorities$Account_getInstance
  });
  var package$org = _.org || (_.org = {});
  var package$arhor = package$org.arhor || (package$org.arhor = {});
  var package$diploma = package$arhor.diploma || (package$arhor.diploma = {});
  Object.defineProperty(package$diploma, 'Authorities', {
    get: Authorities_getInstance
  });
  Object.defineProperty(package$diploma, 'CsrfUtils', {
    get: CsrfUtils_getInstance
  });
  Object.defineProperty(package$diploma, 'Utils', {
    get: Utils_getInstance
  });
  Kotlin.defineModule('diploma-shared', _);
  return _;
}(module.exports, require('kotlin')));
