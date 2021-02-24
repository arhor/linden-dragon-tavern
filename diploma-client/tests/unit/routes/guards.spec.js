import * as guards from '@/routes/guards.js';

describe('composeGuards', () => {
    let mockTo = null;
    let mockFrom = null;
    let mockNext = null;

    beforeEach(() => {
        mockTo = {};
        mockFrom = {};
        mockNext = jest.fn();
    });

    test('should call "next" function exactly once', () => {
        // given
        const guardWithoutAction = guards.composeGuards();

        // when
        guardWithoutAction(mockTo, mockFrom, mockNext);

        // then
        expect(mockNext.mock.calls.length).toBe(1);
    });

    test('should call all guards one by one and call "next" function once', () => {
        // given
        const guard1 = jest.fn().mockImplementation((to, from, next) => next());
        const guard2 = jest.fn().mockImplementation((to, from, next) => next());
        const guard3 = jest.fn().mockImplementation((to, from, next) => next());
        const guardWithoutAction = guards.composeGuards(guard1, guard2, guard3);

        // when
        guardWithoutAction(mockTo, mockFrom, mockNext);

        // then
        expect(guard1).toBeCalledTimes(1);
        expect(guard2).toBeCalledTimes(1);
        expect(guard3).toBeCalledTimes(1);
        expect(mockNext.mock.calls.length).toBe(1);
    });

    test('should call guards up to the first guard passing an argument to the "next" function', () => {
        // given
        const nextArg = 'should be passed to the real "next" function';
        const guard1 = jest.fn().mockImplementation((to, from, next) => next());
        const guard2 = jest.fn().mockImplementation((to, from, next) => next(nextArg));
        const guard3 = jest.fn().mockImplementation((to, from, next) => next());
        const guardWithoutAction = guards.composeGuards(guard1, guard2, guard3);

        // when
        guardWithoutAction(mockTo, mockFrom, mockNext);

        // then
        expect(guard1).toBeCalledTimes(1);
        expect(guard2).toBeCalledTimes(1);
        expect(guard3).toBeCalledTimes(0);
        expect(mockNext.mock.calls).toEqual([[nextArg]]);
    });
});
