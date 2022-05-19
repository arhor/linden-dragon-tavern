import { existsSync, lstatSync, mkdirSync, readdirSync, rmSync, writeFileSync } from 'fs';
import { compileFromFile } from 'json-schema-to-typescript';

const RESOURCES_ROOT = 'src/main/resources/json';
const GENERATED_DIST = 'build/generated/sources/js2ts';

rmSync(GENERATED_DIST, { recursive: true });

(async function main({ source, target }) {
    if (existsSync(source)) {
        const files = readdirSync(source);

        for (const file of files) {
            const fileFullPath = `${source}/${file}`;
            const stats = lstatSync(fileFullPath);

            if (stats.isFile()) {
                if (file.endsWith('.json')) {
                    const ts = await compileFromFile(fileFullPath, { cwd: source });
                    const entityName = file.replace('.json', '');

                    if (!existsSync(target)) {
                        mkdirSync(target, { recursive: true });
                    }
                    writeFileSync(`${target}/${entityName}.d.ts`, ts);
                }
            } else if (stats.isDirectory()) {
                await main({ source: fileFullPath, target: `${target}/${file}` });
            }
        }
    }
})({
    source: RESOURCES_ROOT,
    target: GENERATED_DIST,
});
