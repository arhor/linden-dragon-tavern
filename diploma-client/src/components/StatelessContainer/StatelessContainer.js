import React from 'react';
import PropTypes from 'prop-types';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';

export const TYPE = Object.freeze({
  PAGE: Symbol('page'),
  CARD: Symbol('card'),
});

export const SIZE = Object.freeze({
  SMALL : Symbol('small'),
  MEDIUM: Symbol('medium'),
  LARGE : Symbol('large'),
});

const PAGE_PROPS = Object.freeze({
  style: { transform: 'translate(-50%, -50%)' },
  position: 'absolute',
  top: '50%',
  left: '50%',
});

function calcProps(size) {
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
}

const StatelessContainer = ({
  type = TYPE.PAGE,
  size = SIZE.MEDIUM,
  padding = 2,
  image,
  title,
  description,
  button,
}) => {  
  const { imageWidth, imageHeight, variant } = calcProps(size);

  const BOX_PROPS = (type === TYPE.PAGE) ? PAGE_PROPS : { padding };
    
  if (Object.values(TYPE).includes(type)) {
    return (
      <Box textAlign='center' {...BOX_PROPS}>
        
        {image && (
          <Box
            clone
            mb={title || description ? 2 : 0}
            width={`${imageWidth}%`}
            height={`${imageHeight}%`}
          >
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
            <Typography variant='body1'>{description}</Typography>
          </Box>
        )}

        {button && button}
      </Box>
    );
  }

  return null;
};

StatelessContainer.propTypes = {
  type: PropTypes.oneOf(Object.values(TYPE)),
  size: PropTypes.oneOf(Object.values(SIZE)),
  padding: PropTypes.number,
  image: PropTypes.element,
  title: PropTypes.string,
  description: PropTypes.string,
  button: PropTypes.element,
};

export default StatelessContainer;