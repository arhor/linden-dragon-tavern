import React from 'react';
import PropTypes from 'prop-types';
import { Box, Typography } from '@mui/material';

/**
 * @readonly
 * @enum {string}
 */
export const TYPE = {
    PAGE: 'page',
    CARD: 'card',
};

/**
 * @readonly
 * @enum {string}
 */
export const SIZE = {
    SMALL: 'small',
    MEDIUM: 'medium',
    LARGE: 'large',
};

function determineWidgetParams(size) {
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
        case SIZE.MEDIUM:
            return {
                imageWidth: 60,
                imageHeight: 60,
                variant: 'h5',
            };
        default:
            throw new Error(`Unsupported size: ${size}`);
    }
}

function determineBoxStyle(type, padding) {
    switch (type) {
        case TYPE.CARD:
            return {
                padding,
                textAlign: 'center'
            };
        case TYPE.PAGE:
            return {
                transform: 'translate(-50%, -50%)',
                position: 'absolute',
                top: '50%',
                left: '50%',
                textAlign: 'center',
            };
        default:
            throw new Error(`Unsupported type: ${type}`);
    }
}

StatelessWidget.propTypes = {
    type: PropTypes.oneOf(Object.values(TYPE)),
    size: PropTypes.oneOf(Object.values(SIZE)),
    padding: PropTypes.number,
    image: PropTypes.element,
    title: PropTypes.string,
    description: PropTypes.string,
    button: PropTypes.element,
};

function StatelessWidget({
    type = TYPE.PAGE,
    size = SIZE.MEDIUM,
    padding = 2,
    image,
    title,
    description,
    button,
}) {
    const boxStyle = determineBoxStyle(type, padding);
    const { imageWidth, imageHeight, variant } = determineWidgetParams(size);

    return (
        <Box sx={boxStyle}>
            {image && (
                <Box sx={{ mb: title || description ? 2 : 0, width: `${imageWidth}%`, height: `${imageHeight}%` }}>
                    {image}
                </Box>
            )}
            {title && (
                <Box mb={!description && button ? 2 : 0}>
                    <Typography variant={variant}>{title}</Typography>
                </Box>
            )}
            {description && (
                <Box mb={button && 2}>
                    <Typography variant="body1">{description}</Typography>
                </Box>
            )}
            {button && button}
        </Box>
    );
}

export default StatelessWidget;
