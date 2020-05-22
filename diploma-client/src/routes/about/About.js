import React from 'react';
import StatelessContainer, { TYPE, SIZE } from '@/components/StatelessContainer';

const About = () => {
  return (
    <>
      <StatelessContainer
        type={TYPE.PAGE}
        size={SIZE.LARGE}
        title="It's an Empty State component title"
        description="It's an Empty State component description"
      />
    </>
  );
};

export default About;