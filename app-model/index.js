import { existsSync, lstatSync, mkdirSync, readdirSync, writeFileSync } from 'fs';
import { compileFromFile } from 'json-schema-to-typescript';

(async function main({ source, target }) {
    if (existsSync(source)) {
        const files = readdirSync(source);

        for (const file of files) {
            const fileFullPath = `${source}/${file}`;
            const stats = lstatSync(fileFullPath);

            if (stats.isFile()) {
                if (file.endsWith('.json')) {
                    const ts = await compileFromFile(fileFullPath);
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
    source: 'src/main/resources/json',
    target: 'build/generated/sources/js2ts'
});
