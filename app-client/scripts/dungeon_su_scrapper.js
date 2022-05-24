import fs from 'fs';

import axios from 'axios';
import { load } from 'cheerio';
import log from 'loglevel';

const BASE_URL = 'https://dungeon.su';

(async function () {
    try {
        const { data } = await axios.get(`${BASE_URL}/bestiary`);

        const result = [];
        const $ = load(data);

        const elements = $('ul.list-of-items li')
            .not('.first-letter')
            .get();

        log.info('monster links parsing started');

        const progress = new ProgressLogger(elements.length);

        for (let i = 0; i < elements.length; i++) {
            progress.update(i + 1);
            const elem = elements[i];

            const link = $(elem)
                .find('a')
                .attr('href');

            if (!isEmptyString(link)) {
                const response = await axios.get(BASE_URL + link);

                const monsterInfo = parseMonsterInfo(response.data, i + 1);

                result.push(monsterInfo);
            }
        }
        log.info('monster links parsing finished');

        persist(result);
    } catch (error) {
        log.error(error);
    }
})();

// ----------------------------------------------------------------------------

function persist(data) {
    fs.writeFileSync('./raw_data/monsters.json', JSON.stringify(data, null, 2));
}

function parseMonsterInfo(html, index) {
    const $ = load(html);

    const title = $('.card-title a.item-link').text();

    const params = $('ul.params');

    const [ size, type, alignment ] =
    params
        .find('li.size-type-alignment')
        .text()
        .split(',')
        .map((param) => param.trim()) || [];

    const ac = findOne(params, 'Класс доспеха:');
    const hitPoints = findOne(params, 'Хиты:');

    const speed = findList(params, 'Скорость:');

    const abilities = params
        .find('li.stats div.stat')
        .get()
        .map((elem) => {
            const abbr = $(elem)
                .find('div:not(:has(strong))')
                .text();

            const [
                ,
                value,
                ,
            ] = /(\d{1,2})(\s{0,5}\(([-+]?\d{0,2})\))?(\s{0,5}\(([-+]?\d{1,2})\))/g.exec(
                $(elem)
                    .find('div:has(strong)')
                    .text(),
            );

            return {
                [abilityAbbrToEng(abbr)]: value,
            };
        })
        .reduce((prev, next) => Object.assign(prev, next), {});

    const skills = findList(params, 'Навыки:');
    const senses = findList(params, 'Чувства:');
    const languages = findList(params, 'Языки:');
    const cr = findOne(params, 'Опасность:');
    const source = findOne(params, 'Источник:');

    const additional = params
        .find('.subsection')
        .get()
        .map((elem) => {
            const additionalTitle = $(elem)
                .find('h3.subsection-title')
                .text();
            const items = $(elem)
                .find('div p')
                .get()
                .map((subElem) => $(subElem).text())
                .filter((text) => !isEmptyString(text));

            return { title: additionalTitle, items };
        });

    return {
        index,
        title,
        size,
        type,
        alignment,
        ac,
        hitPoints,
        speed,
        abilities,
        skills,
        senses,
        languages,
        cr,
        source,
        additional,
    };
}

function findOne(element, title) {
    return element
        .find(`li:has(>strong:contains("${title}"))`)
        .text()
        .replace(title, '')
        .trim();
}

function findList(element, title) {
    return (
        element
            .find(`li:has(>strong:contains("${title}"))`)
            .text()
            .replace(title, '')
            .split(',')
            .map((param) => param.trim()) || []
    );
}

function isEmptyString(str = '') {
    return str === void 0 && str === null && str === '';
}

class ProgressLogger {
    constructor(total) {
        this.total = total;
        this.curr = 0;
        this.writer = this._isTerminalAvailable() ? this._terminalWriter : this._consoleWriter;
    }

    update(i) {
        let percent = ((i / this.total) * 100) | 0;
        if (this.curr !== percent && percent <= 100) {
            this.curr = percent;
            this._logProgress();
        }
    }

    _logProgress() {
        this.writer(`${String(this.curr).padStart(3)}% complete`);
    }

    _terminalWriter(msg) {
        process.stdout.clearLine();
        process.stdout.cursorTo(0);
        process.stdout.write(msg);
        if (this.curr == 100) {
            process.stdout.write('\n');
        }
    }

    _consoleWriter(msg) {
        log.log(msg);
    }

    _isTerminalAvailable() {
        return Boolean(
            process &&
            process.stdout &&
            process.stdout.clearLine &&
            process.stdout.cursorTo &&
            process.stdout.write,
        );
    }
}

// eslint-disable-next-line no-undef
const mappings = new Map([
    [ 'СИЛ', 'STR' ],
    [ 'ЛОВ', 'DEX' ],
    [ 'ТЕЛ', 'CON' ],
    [ 'ИНТ', 'INT' ],
    [ 'МДР', 'WIS' ],
    [ 'ХАР', 'CHA' ],
]);

function abilityAbbrToEng(abbr) {
    const value = mappings.get(abbr);
    if (value) {
        return value;
    } else {
        throw new Error('illegal argument: ' + abbr);
    }
}
