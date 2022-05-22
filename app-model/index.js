import { existsSync, lstatSync, mkdirSync, readdirSync, rmSync, writeFileSync } from 'fs';
import { compileFromFile } from 'json-schema-to-typescript';
import path from 'path';

const RESOURCES_ROOT = 'src/main/resources/json';
const GENERATED_DIST = 'build/generated/sources/js2ts';

rmSync(GENERATED_DIST, { force: true, recursive: true });

await (async function main({ source, target }) {
    if (existsSync(source)) {
        const files = readdirSync(source);

        for (const file of files) {
            const fileFullPath = `${source}/${file}`;
            const stats = lstatSync(fileFullPath);

            if (stats.isFile()) {
                if (file.endsWith('.json')) {
                    const dependencies = [];
                    const compiledSchema = await compileFromFile(fileFullPath, {
                        cwd: source,
                        $refOptions: {
                            resolve: {
                                file: {
                                    read: ({ url, extension }) => {
                                        if (extension === '.json') {
                                            const importPath =
                                                path.relative(source, url)
                                                    .split(path.sep)
                                                    .join(path.posix.sep)
                                                    .replace(extension, '');
                                            dependencies.push(
                                                importPath.startsWith('../')
                                                    ? importPath
                                                    : `./${importPath}`
                                            );
                                        }
                                        return '{}';
                                    }
                                },
                            },
                        },
                    });
                    mkdirSync(target, { recursive: true });

                    const entityName = file.replace('.json', '');
                    const [ firstLine, ...restOfTheFile ] = compiledSchema.split(/\r?\n/);

                    writeFileSync(`${target}/${entityName}.d.ts`, [
                        firstLine,
                        ...dependencies.map(path => `import { ${path.replace(/[./]/g, '')} } from '${path}';`),
                        '',
                        ...restOfTheFile,
                    ].join('\n'));
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
