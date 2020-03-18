import dictionary from '@/langs'
import store from '@/store'

export default function translate(label) {
  const { currentLang } = store.state
  const translation = dictionary[currentLang][label]
  return (translation === undefined || translation === null)
      ? 'ERROR: missing label'
      : translation
}