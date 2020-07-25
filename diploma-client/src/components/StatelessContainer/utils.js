import { SIZE } from '@/components/StatelessContainer/constants';

export const calcProps = (size) => {
    switch (size) {
        case SIZE.SMALL:
            return {
                imageWidth: 40,
                imageHeight: 40,
                variant: 'h6',
            };
        case SIZE.LARGE:
            return {
                imageWidth: 100,
                imageHeight: 100,
                variant: 'h4',
            };
        default:
            return {
                imageWidth: 60,
                imageHeight: 60,
                variant: 'h5',
            };
    }
};
