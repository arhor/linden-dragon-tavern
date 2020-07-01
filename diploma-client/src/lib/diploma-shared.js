(function (_, Kotlin) {
  'use strict';
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
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
  Object.defineProperty(Authorities.prototype, 'Account', {
    get: Authorities$Account_getInstance
  });
  var package$org = _.org || (_.org = {});
  var package$arhor = package$org.arhor || (package$org.arhor = {});
  var package$diploma = package$arhor.diploma || (package$arhor.diploma = {});
  Object.defineProperty(package$diploma, 'Authorities', {
    get: Authorities_getInstance
  });
  Kotlin.defineModule('diploma-shared', _);
  return _;
}(module.exports, require('kotlin')));
