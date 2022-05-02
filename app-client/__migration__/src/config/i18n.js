import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import { default as EN } from '@/assets/lang/en.json';
import { default as RU } from '@/assets/lang/ru.json';

i18n.use(initReactI18next).init({
    resources: {
        en: { translation: EN },
        ru: { translation: RU },
    },
    lng: 'en',
    interpolation: {
        escapeValue: false,
    },
});

export default i18n;
