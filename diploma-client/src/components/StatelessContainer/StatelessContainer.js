import React from 'react';
import PropTypes from 'prop-types';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import { SIZE, TYPE, PAGE_PROPS } from '@/components/StatelessContainer/constants';
import { calcProps } from '@/components/StatelessContainer/utils';

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