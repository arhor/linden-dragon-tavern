import React from 'react';
import PropTypes from 'prop-types';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';

export const TYPE = Object.freeze({
  PAGE: 'page',
  CARD: 'card',
});

export const SIZE = Object.freeze({
  SMALL: 'small',
  MEDIUM: 'medium',
  LARGE: 'large',
});

const StatelessContainer = ({
  type = TYPE.PAGE,
  size = SIZE.MEDIUM,
  padding = 2,
  image,
  title,
  description,
  button,
}) => {  
  let imageWidth;
  let imageHeight;
  let variant;

  switch (size) {
    case SIZE.SMALL:
      imageWidth = 40;
      imageHeight = 40;
      variant = "h6";
      break;

    case SIZE.MEDIUM:
      imageWidth = 60;
      imageHeight = 60;
      variant = "h5";
      break;

    case SIZE.LARGE:
      imageWidth = 100;
      imageHeight = 100;
      variant = "h4";
      break;

    default:
      imageWidth = 60;
      imageHeight = 60;
      variant = "h5";
      break;
  }

  const BOX_PROPS = (type === TYPE.PAGE)
    ? {
      style: { transform: "translate(-50%, -50%)" },
      position:"absolute",
      top:"50%",
      left:"50%",
    }
    : {
      padding: padding,
    };

  if (type === TYPE.PAGE || type === TYPE.CARD) {
    return (
      <Box textAlign="center" {...BOX_PROPS}>

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
            <Typography variant="body1">{description}</Typography>
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