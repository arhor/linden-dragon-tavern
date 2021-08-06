(function(_, Kotlin) {
    'use strict';
    var Kind_OBJECT = Kotlin.Kind.OBJECT;
    var Enum = Kotlin.kotlin.Enum;
    var Kind_CLASS = Kotlin.Kind.CLASS;
    var throwISE = Kotlin.throwISE;
    var numberToInt = Kotlin.numberToInt;
    Role.prototype = Object.create(Enum.prototype);
    Role.prototype.constructor = Role;
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
        interfaces: [],
    };
    var Authorities$Account_instance = null;
    function Authorities$Account_getInstance() {
        if (Authorities$Account_instance === null) {
            new Authorities$Account();
        }
        return Authorities$Account_instance;
    }
    Authorities.$metadata$ = {
        kind: Kind_OBJECT,
        simpleName: 'Authorities',
        interfaces: [],
    };
    var Authorities_instance = null;
    function Authorities_getInstance() {
        if (Authorities_instance === null) {
            new Authorities();
        }
        return Authorities_instance;
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
        interfaces: [],
    };
    var CsrfUtils_instance = null;
    function CsrfUtils_getInstance() {
        if (CsrfUtils_instance === null) {
            new CsrfUtils();
        }
        return CsrfUtils_instance;
    }
    function JwtStruct() {
        JwtStruct_instance = this;
        this.USERNAME = 'username';
        this.ROLES = 'roles';
    }
    JwtStruct.$metadata$ = {
        kind: Kind_OBJECT,
        simpleName: 'JwtStruct',
        interfaces: [],
    };
    var JwtStruct_instance = null;
    function JwtStruct_getInstance() {
        if (JwtStruct_instance === null) {
            new JwtStruct();
        }
        return JwtStruct_instance;
    }
    function Role(name, ordinal, persistent) {
        if (persistent === void 0) persistent = true;
        Enum.call(this);
        this.persistent = persistent;
        this.name$ = name;
        this.ordinal$ = ordinal;
    }
    function Role_initFields() {
        Role_initFields = function() {};
        Role$USER_instance = new Role('USER', 0);
        Role$ADMIN_instance = new Role('ADMIN', 1);
        Role$ANONYMOUS_instance = new Role('ANONYMOUS', 2, false);
    }
    var Role$USER_instance;
    function Role$USER_getInstance() {
        Role_initFields();
        return Role$USER_instance;
    }
    var Role$ADMIN_instance;
    function Role$ADMIN_getInstance() {
        Role_initFields();
        return Role$ADMIN_instance;
    }
    var Role$ANONYMOUS_instance;
    function Role$ANONYMOUS_getInstance() {
        Role_initFields();
        return Role$ANONYMOUS_instance;
    }
    Role.prototype.prefixed = function() {
        return 'ROLE_' + this.toString();
    };
    Role.$metadata$ = {
        kind: Kind_CLASS,
        simpleName: 'Role',
        interfaces: [Enum],
    };
    function Role$values() {
        return [Role$USER_getInstance(), Role$ADMIN_getInstance(), Role$ANONYMOUS_getInstance()];
    }
    Role.values = Role$values;
    function Role$valueOf(name) {
        switch (name) {
            case 'USER':
                return Role$USER_getInstance();
            case 'ADMIN':
                return Role$ADMIN_getInstance();
            case 'ANONYMOUS':
                return Role$ANONYMOUS_getInstance();
            default:
                throwISE('No enum constant org.arhor.diploma.Role.' + name);
        }
    }
    Role.valueOf_61zpoe$ = Role$valueOf;
    function Utils() {
        Utils_instance = this;
    }
    Utils.prototype.calcAbilityModifier = function(value) {
        var tmp$;
        var modifier = (((numberToInt(value) - 10) | 0) / 2) | 0;
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
        interfaces: [],
    };
    var Utils_instance = null;
    function Utils_getInstance() {
        if (Utils_instance === null) {
            new Utils();
        }
        return Utils_instance;
    }
    Object.defineProperty(Authorities.prototype, 'Account', {
        get: Authorities$Account_getInstance,
    });
    var package$org = _.org || (_.org = {});
    var package$arhor = package$org.arhor || (package$org.arhor = {});
    var package$diploma = package$arhor.diploma || (package$arhor.diploma = {});
    Object.defineProperty(package$diploma, 'Authorities', {
        get: Authorities_getInstance,
    });
    Object.defineProperty(package$diploma, 'CsrfUtils', {
        get: CsrfUtils_getInstance,
    });
    Object.defineProperty(package$diploma, 'JwtStruct', {
        get: JwtStruct_getInstance,
    });
    Object.defineProperty(Role, 'USER', {
        get: Role$USER_getInstance,
    });
    Object.defineProperty(Role, 'ADMIN', {
        get: Role$ADMIN_getInstance,
    });
    Object.defineProperty(Role, 'ANONYMOUS', {
        get: Role$ANONYMOUS_getInstance,
    });
    package$diploma.Role = Role;
    Object.defineProperty(package$diploma, 'Utils', {
        get: Utils_getInstance,
    });
    Kotlin.defineModule('diploma-shared', _);
    return _;
})(module.exports, require('kotlin'));
