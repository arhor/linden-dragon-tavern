import i18n from 'i18next';
import log from 'loglevel';
import { initReactI18next } from 'react-i18next';

import { default as EN } from '@/assets/lang/en.json';
import { default as RU } from '@/assets/lang/ru.json';

i18n.use(initReactI18next);

i18n.init({
    resources: {
        en: { translation: EN },
        ru: { translation: RU },
    },
    lng: 'en',
    interpolation: {
        escapeValue: false,
    },
}).then(() => {
    log.info('Localization module loaded successfully');
}).catch((e) => {
    log.error('Localization module loading failure', e);
});

export default i18n;
