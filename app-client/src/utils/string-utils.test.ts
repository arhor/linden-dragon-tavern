import * as stringUtils from '@/utils/string-utils';

describe('renderLinebreaksHTML', () => {
    test.each([
        ['line 1\nline2\nline3'],
        ['line 1\r\nline2\r\nline3'],
        ['line 1\nline2\r\nline3'],
        ['line 1\r\nline2\nline3'],
    ])('should replace all linebreaks with <br/> tag', (line) => {
        // when
        const result = stringUtils.renderLinebreaksHTML(line);

        // then
        expect(result).toEqual('line 1<br/>line2<br/>line3');
    });
});

describe('signed', () => {
    test.each([
        [0],
        [5],
        [1000],
    ])('should sign non-negative numbers with `+`', (number) => {
        // when
        const result = stringUtils.signed(number);

        // then
        expect(result).toEqual(`+${number}`);
    });

    test.each([[-1], [-5], [-1000]])('should sign negative numbers with `-`', (number) => {
        // when
        const result = stringUtils.signed(number);

        // then
        expect(result).toEqual(`${number}`);
    });
});

describe('replaceSpacesWithUnderscore', () => {
    test('should convert all spaces into underscores and convert string to lower case', () => {
        // given
        const text = 'Test property name';

        // when
        const result = stringUtils.replaceSpacesWithUnderscore(text);

        // then
        expect(result).toEqual('test_property_name');
    });
});

describe('serialize', () => {
    test('should map object to string with format: "name=value;" for each property', () => {
        // given
        const object = { name: 'Max', age: 31, hungry: false, dev: 'true' };

        // when
        const result = stringUtils.serialize(object);

        // then
        expect(result).toContain(`name=${object.name};`);
        expect(result).toContain(`age=${object.age};`);
        expect(result).toContain(`hungry=${object.hungry};`);
        expect(result).toContain(`dev;`);
    });

    test.each([
        [{}],
        [null],
        [undefined],
    ])('should return empty string for "%s"', (object) => {
        // when
        const result = stringUtils.serialize(object);

        // then
        expect(result).toEqual('');
    });
});

describe('deserialize', () => {
    test('should map string into object', () => {
        // given
        const string = 'enabled;timeout=10;';

        // when
        const result = stringUtils.deserialize(string);

        // then
        expect(result).toHaveProperty('enabled', true);
        expect(result).toHaveProperty('timeout', '10');
    });

    test.each([
        [''],
        [';;;'],
        [null],
        [undefined],
    ])('should return empty object for "%s"', (string: string | null | undefined) => {
        // when
        const result = stringUtils.deserialize(string);

        // then
        expect(result).toEqual({});
    });
});
