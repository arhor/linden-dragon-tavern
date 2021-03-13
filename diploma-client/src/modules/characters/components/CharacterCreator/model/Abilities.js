const ABILITY_DEFAULT_VALUE = 10;

export default class Abilities {
    constructor({
        str = ABILITY_DEFAULT_VALUE,
        dex = ABILITY_DEFAULT_VALUE,
        con = ABILITY_DEFAULT_VALUE,
        int = ABILITY_DEFAULT_VALUE,
        wis = ABILITY_DEFAULT_VALUE,
        cha = ABILITY_DEFAULT_VALUE,
    } = {}) {
        this.str = str;
        this.dex = dex;
        this.con = con;
        this.int = int;
        this.wis = wis;
        this.cha = cha;
    }

    clone({ str, dex, con, int, wis, cha } = {}) {
        return new Abilities({
            str: str ?? this.str,
            dex: dex ?? this.dex,
            con: con ?? this.con,
            int: int ?? this.int,
            wis: wis ?? this.wis,
            cha: cha ?? this.cha,
        });
    }
}
