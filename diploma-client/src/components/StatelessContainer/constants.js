export const TYPE = Object.freeze({
    PAGE: Symbol('page'),
    CARD: Symbol('card'),
});

export const SIZE = Object.freeze({
    SMALL: Symbol('small'),
    MEDIUM: Symbol('medium'),
    LARGE: Symbol('large'),
});

export const PAGE_PROPS = Object.freeze({
    style: {
        transform: 'translate(-50%, -50%)',
    },
    position: 'absolute',
    top: '50%',
    left: '50%',
});
