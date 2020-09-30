import * as stringUtils from '@/utils/stringUtils.js';

describe('stringUtils.js', () => {
    test('serialize should map object to string with format: "name=value;" for each property', () => {
        const object = { name: 'Max', age: 31, hungry: false, dev: 'true' };
        const result = stringUtils.serialize(object);

        expect(result).toContain(`name=${object.name};`);
        expect(result).toContain(`age=${object.age};`);
        expect(result).toContain(`hungry=${object.hungry};`);
        expect(result).toContain(`dev;`);
    });

    test('deserialize should map string into object', () => {
        const string = 'enabled;timeout=10;';
        const result = stringUtils.deserialize(string);

        expect(result).toHaveProperty('enabled', true);
        expect(result).toHaveProperty('timeout', '10');
    });
});
