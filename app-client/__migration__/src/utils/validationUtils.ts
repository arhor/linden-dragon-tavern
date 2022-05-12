import { Optional } from '@/utils/coreUtils';

export type ValidationRule<T> = (v: Optional<T>) => true | string;

export type ValidationRules<T> = Partial<{ [P in keyof T]: ValidationRule<T[P]>[] }>;

export type ValidationErrors<T> = Partial<{ [P in keyof T]: string }>;

export type ValidationFunction<T> = (values: T) => ValidationErrors<T>

export const defineValidator = <T>(rules: ValidationRules<T>): ValidationFunction<T> => (values: T) => {
    const errors = {} as ValidationErrors<T>;

    if (values) {
        for (const name in values) {
            type Test<T> = Optional<ValidationRule<T[keyof T]>[]>;

            const currentRules = rules[name] as Optional<ValidationRule<T[keyof T]>[]>;

            if (currentRules) {
                for (const rule of currentRules) {
                    const value = values[name];
                    const result = rule(value);

                    if (result !== true) {
                        errors[name] = result;
                        break;
                    }
                }
            }
        }
    }
    return errors;
};
