export default class Origin {
    constructor({ name = '', proficiencies = [] } = {}) {
        this.name = name;
        this.proficiencies = proficiencies;
    }

    clone({ name, proficiencies } = {}) {
        return new Origin({
            name: name ?? this.name,
            proficiencies: proficiencies ?? this.proficiencies,
        });
    }
}
