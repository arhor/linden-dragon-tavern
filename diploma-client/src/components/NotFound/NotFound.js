import React from 'react';
import StatelessContainer, { TYPE, SIZE } from '@/components/StatelessContainer';

const NotFound = () => {
  return (
    <>
      <StatelessContainer
        type={TYPE.PAGE}
        size={SIZE.LARGE}
        title="Ups, page not found..."
        description="Please, try to find somewhere else :)"
      />
    </>
  );
};

export default NotFound;