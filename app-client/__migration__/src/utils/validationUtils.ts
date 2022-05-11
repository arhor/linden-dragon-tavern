import { Optional } from '@/utils/coreUtils';

export type Rule<T> = (v: Optional<T>) => true | string;

export type ValidationRules = { [key: string]: (Rule<string>)[] };

export const defineValidator = (rules: ValidationRules) => (values: object) => {
    const errors: { [key: string]: string } = {}

    for (const [name, value] of Object.entries(values)) {
        const currentRules = rules[name];
        if (currentRules) {
            for (const rule of currentRules) {
                const result = rule(value);
                if (result !== true) {
                    errors[name] = result;
                    break;
                }
            }
        }
    }
    return errors;
};
