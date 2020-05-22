import React from 'react';
import PropTypes from 'prop-types';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';

export const TYPE = {
  PAGE: 'page',
  CARD: 'card',
};

export const SIZE = {
  SMALL: 'small',
  MEDIUM: 'medium',
  LARGE: 'large',
};

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

  const displayImage = () => {
    return image && (
      <Box
        clone
        mb={title || description ? 2 : 0}
        width={`${imageWidth}%`}
        height={`${imageHeight}%`}
      >
        {image}
      </Box>
    );
  };

  const displayTitle = () => {
    return title && (
      <Box mb={!description && button ? 2 : 0}>
        <Typography variant={variant}>{title}</Typography>
      </Box>
    );
  };

  const displayDescription = () => {
    return description && (
      <Box mb={button && 2}>
        <Typography variant="body1">{description}</Typography>
      </Box>
    ); 
  };

  const displayButton = () => {
    return button && button;
  };

  if (type === TYPE.PAGE) {
    return (
      <Box
        style={{ transform: "translate(-50%, -50%)" }}
        position="absolute"
        top="50%"
        left="50%"
        textAlign="center"
      >
        {displayImage()}
        {displayTitle()}
        {displayDescription()}
        {displayButton()}
      </Box>
    );
  }

  if (type === TYPE.CARD) {
    return (
      <Box padding={padding} textAlign="center">
        {displayImage()}
        {displayTitle()}
        {displayDescription()}
        {displayButton()}
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