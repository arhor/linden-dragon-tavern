import store from '@/store';
import { refExists } from '@/util/helpers.js';

export default function translate(label) {
  const translation = store.getters['localization/dictionary'](label);

  return refExists(translation)
      ? translation
      : 'ERROR: missing label';
}
