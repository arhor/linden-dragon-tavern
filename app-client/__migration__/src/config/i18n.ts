import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import { default as EN } from '@/assets/lang/en.json';
import { default as RU } from '@/assets/lang/ru.json';
import axios from "axios";

i18n.use(initReactI18next)
    .init({
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


// export async function loadLanguageAsync(lang) {
//     if (!refExists(lang)) {
//         throw Error('lang must not be undefined or null');
//     }
//     if (i18n.locale !== lang) {
//         if (!loadedLanguages.includes(lang)) {
//             // TODO: fix the issue with lazy lang-pack loading
//             const data = await import(/* webpackChunkName: "lang-[request]" */ `@/assets/lang/${lang}.json`);
//             i18n.setLocaleMessage(lang, data.default);
//             loadedLanguages.push(lang);
//         }
//         i18n.locale = lang;
//         axios.defaults.headers.common['Accept-Language'] = lang;
//         document.querySelector('html').setAttribute('lang', lang);
//     }
//     return lang;
// }
