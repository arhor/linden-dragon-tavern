import * as stringUtils from '@/utils/stringUtils.js';

describe('stringUtils.js', () => {
    test('should replace all linebreaks with <br/> tag', () => {
        const lines = [
            'line 1\nline2\nline3',
            'line 1\r\nline2\r\nline3',
            'line 1\nline2\r\nline3',
        ];

        lines.forEach((line) => {
            const result = stringUtils.renderLinebreaksHTML(line);

            expect(result).toEqual('line 1<br/>line2<br/>line3');
        });
    });

    test('should sign non-negative numbers with `+`', () => {
        const numbers = [0, 5, 1000];

        numbers.forEach((number, i) => {
            const result = stringUtils.signed(number);

            expect(result).toEqual('+' + numbers[i]);
        });
    });

    test('should sign negative numbers with `-`', () => {
        const numbers = [-1, -5, -1000];

        numbers.forEach((number, i) => {
            const result = stringUtils.signed(number);

            expect(result).toEqual('-' + numbers[i]);
        });
    });

    test('should convert all spaces into underscores and convert string to lower case', () => {
        const text = 'Test property name';
        const result = stringUtils.replaceSpacesWithUnderscore(text);

        expect(result).toEqual('test_property_name');
    });

    test('should map object to string with format: "name=value;" for each property', () => {
        const object = { name: 'Max', age: 31, hungry: false, dev: 'true' };
        const result = stringUtils.serialize(object);

        expect(result).toContain(`name=${object.name};`);
        expect(result).toContain(`age=${object.age};`);
        expect(result).toContain(`hungry=${object.hungry};`);
        expect(result).toContain(`dev;`);
    });

    test('should return empty string for {}, null or undefined', () => {
        const objects = [{}, null, undefined];

        objects.forEach((object) => {
            const result = stringUtils.serialize(object);

            expect(result).toEqual('');
        });
    });

    test('should map string into object', () => {
        const string = 'enabled;timeout=10;';
        const result = stringUtils.deserialize(string);

        expect(result).toHaveProperty('enabled', true);
        expect(result).toHaveProperty('timeout', '10');
    });

    test('should return empty object for empty string with any number of `;`, null or undefined', () => {
        const strings = ['', ';;;', null, undefined];

        strings.forEach((string) => {
            const result = stringUtils.deserialize(string);

            expect(result).toEqual({});
        });
    });
});
