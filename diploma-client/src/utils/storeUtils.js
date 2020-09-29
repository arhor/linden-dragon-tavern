/**
 * Provides a plugin instance which persists store state on any mutation.
 *
 * @param storeName {string} name of the store module will be used as a key for local storage
 * @return {function({subscribe: *}): void}
 */
export function useLocalStoragePlugin(storeName) {
    return ({ subscribe }) => {
        subscribe((mutation, state) => {
            localStorage.setItem(storeName, JSON.stringify({ ...state }));
        });
    };
}

/**
 * Provides a plugin which allows to handle mutation events by their type.
 *
 * @param mutationHandler {object} options object, should contain mappings between mutation types and handler functions
 * @return {function({subscribe: *}): void}
 */
export function useMutationHandlerPlugin(mutationHandler) {
    return ({ subscribe }) => {
        subscribe((mutation, state) => {
            mutationHandler?.[mutation.type]?.(state);
        });
    };
}
