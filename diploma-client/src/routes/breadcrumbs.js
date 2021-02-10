export class Breadcrumb {
    constructor({ label, link }) {
        this.label = label;
        this.link = link;
    }
}

export const home = new Breadcrumb({
    label: 'views.home',
    link: '/',
});

export const about = new Breadcrumb({
    label: 'About',
    link: '/about',
});

export const monsters = new Breadcrumb({
    label: 'Monsters',
    link: '/monsters',
});

export const spells = new Breadcrumb({
    label: 'Spells',
    link: '/spells',
});

export const maps = new Breadcrumb({
    label: 'Maps',
    link: '/maps',
});
