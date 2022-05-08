import { ReactElement } from 'react';

import { Box, Typography } from '@mui/material';
import { SxProps } from "@mui/system";

export type Type = 'page' | 'card';

export type Size = 'small' | 'medium' | 'large';

function determineWidgetParams(size: Size): {
    imageWidth: number,
    imageHeight: number,
    variant: 'h4' | 'h5' | 'h6',
} {
    switch (size) {
        case 'small':
            return {
                imageWidth: 40,
                imageHeight: 40,
                variant: 'h6',
            };
        case 'large':
            return {
                imageWidth: 100,
                imageHeight: 100,
                variant: 'h4',
            };
        case 'medium':
        default:
            return {
                imageWidth: 60,
                imageHeight: 60,
                variant: 'h5',
            };
    }
}

function determineBoxStyle(type: Type, padding: number): SxProps {
    switch (type) {
        case 'card':
            return {
                padding,
                textAlign: 'center'
            };
        case 'page':
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

type StatelessWidgetProps = {
    type?: Type;
    size?: Size;
    padding?: number;
    image?: ReactElement;
    title?: string;
    description?: string;
    button?: ReactElement;
};

const StatelessWidget = ({
    type = 'page',
    size = 'large',
    padding = 2,
    image,
    title,
    description,
    button,
}: StatelessWidgetProps) => {
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
};

export default StatelessWidget;
