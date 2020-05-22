import React from 'react';
import { useTranslation } from 'react-i18next';

const SUPPORTED_LANGS = ['ru', 'en'];

const LangSelector = () => {
  const { i18n } = useTranslation();

  return (
    <select>
      {SUPPORTED_LANGS.map(lang => (
        <option key={lang} onClick={() => i18n.changeLanguage(lang)}>
          {lang.toUpperCase()}
        </option>
      ))}
    </select>
  );
};

export default LangSelector;