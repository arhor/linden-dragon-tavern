import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';

import baroviaregion from '@/assets/img/maps/baroviaregion.png';
import krezk from '@/assets/img/maps/krezk.png';
import vallaki from '@/assets/img/maps/vallaki.png';
import villageofbarovia from '@/assets/img/maps/villageofbarovia.png';

const ITEMS: Readonly<{ src: string; title: string }[]> = [
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
];

const enrich = (src: string): string => `${src}?w=164&h=164&fit=crop&auto=format`;

const MapCarousel = () => (
    <ImageList sx={{ width: 500, height: 450 }} cols={3} rowHeight={164}>
        {ITEMS.map(({ src, title }) => {
            const enrichedSrc = enrich(src);
            return (
                <ImageListItem key={src}>
                    <img src={enrichedSrc} srcSet={`${enrichedSrc}&dpr=2 2x`} alt={title} loading="lazy" />
                </ImageListItem>
            );
        })}
    </ImageList>
);

export default MapCarousel;
