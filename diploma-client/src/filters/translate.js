import store from '@/store';

export default function translate(label) {
  const { currLang } = store.state;
  const dictionary = store.getters['translations/getDictionary'];
  const translation = dictionary(currLang, label);
  return (translation === undefined || translation === null)
      ? 'ERROR: missing label'
      : translation;
}
