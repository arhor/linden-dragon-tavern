import Vue from 'vue';
import VueI18n from 'vue-i18n';
import axios from 'axios';

import { default as messages } from '@/assets/lang/ru.json';
import { refExists } from '@/utils/coreUtils';

Vue.use(VueI18n);

const i18n = new VueI18n({
    locale: 'ru',
    fallbackLocale: 'ru',
    messages,
});

export default i18n;

const loadedLanguages = ['ru'];

export async function loadLanguageAsync(lang) {
    if (!refExists(lang)) {
        throw Error('lang must not be undefined or null');
    }
    if (i18n.locale !== lang) {
        if (!loadedLanguages.includes(lang)) {
            const data = await import(
                /* webpackChunkName: "lang-[request]" */ `@/assets/lang/${lang}.json`
            );
            i18n.setLocaleMessage(lang, data.default);
            loadedLanguages.push(lang);
        }
        i18n.locale = lang;
        axios.defaults.headers.common['Accept-Language'] = lang;
        document.querySelector('html').setAttribute('lang', lang);
    }
    return lang;
}
