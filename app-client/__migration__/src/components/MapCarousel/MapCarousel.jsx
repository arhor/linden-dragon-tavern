import React from 'react';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';

import baroviaregion from '@/assets/img/maps/baroviaregion.png';
import krezk from '@/assets/img/maps/krezk.png';
import vallaki from '@/assets/img/maps/vallaki.png';
import villageofbarovia from '@/assets/img/maps/villageofbarovia.png';

const ITEMS = Object.freeze([
    {
        src: baroviaregion,
        title: '',
    },
    {
        src: krezk,
        title: '',
    },
    {
        src: vallaki,
        title: '',
    },
    {
        src: villageofbarovia,
        title: '',
    },
]);


const enrich = (src) => `${src}?w=164&h=164&fit=crop&auto=format`;

function MapCarousel() {
    return (
        <ImageList sx={{ width: 500, height: 450 }} cols={3} rowHeight={164}>
            {ITEMS.map(({src, title}) => (
                <ImageListItem key={src}>
                    <img src={enrich(src)} srcSet={`${enrich(src)}&dpr=2 2x`} alt={title} loading="lazy" />
                </ImageListItem>
            ))}
        </ImageList>
    );
}

export default MapCarousel;
