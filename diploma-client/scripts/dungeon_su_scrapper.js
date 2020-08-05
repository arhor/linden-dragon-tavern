const axios = require('axios');
const cheerio = require('cheerio');
const fs = require('fs');

const BASE_URL = 'https://dungeon.su';

(async function () {
    try {
        const { data } = await axios.get(`${BASE_URL}/bestiary`);

        const result = [];
        const $ = cheerio.load(data);

        const elements = $('ul.list-of-items li').not('.first-letter').get();

        console.info('monster links parsing started');

        let percent = 0;

        for (let i = 0; i < elements.length; i++) {

            let currPercent = ((i / elements.length) * 100) | 0;
            if (currPercent !== percent) {
                percent = currPercent;
                console.log(`${percent < 10 ? '  ' : percent < 100 ? ' ': ''}${percent}% complete`)
            }

            const elem = elements[i];

            const link = $(elem).find('a').attr('href');

            if (!isEmptyString(link)) {
                const { data } = await axios.get(BASE_URL + link);

                const monsterInfo = parseMonsterInfo(data, i + 1);

                result.push(monsterInfo);
            }
        }
        console.info('monster links parsing finished');

        fs.writeFileSync('./data/monsters.json', JSON.stringify(result, null, 2));
    } catch (error) {
        console.error(error);
    }
})();

// ----------------------------------------------------------------------------

function parseMonsterInfo(html, index) {
    const $ = cheerio.load(html);

    const title = $('.card-title a.item-link').text();

    const params = $('ul.params');

    const [size, type, alignment] =
        params
            .find('li.size-type-alignment')
            .text()
            .split(',')
            .map((param) => param.trim()) || [];

    const ac = findOne(params, 'Класс доспеха:');
    const hp = findOne(params, 'Хиты:');

    const speed = findList(params, 'Скорость:');

    const abilities = params
        .find('li.stats div.stat')
        .get()
        .map((elem) => {
            const title = $(elem).attr('title');
            const abbr = $(elem).find('div:not(:has(strong))').text();
            const ability = $(elem).find('div:has(strong)').text();

            const [
                ,
                value,
                unknown,
                modifier,
            ] = /(\d{1,2})(\s{0,5}\(([\-+]?\d{0,2})\))?(\s{0,5}\(([\-+]?\d{1,2})\))/g.exec(ability);

            return {
                title,
                abbr,
                value,
                unknown,
                modifier,
            };
        });

    const skills = findList(params, 'Навыки:');
    const senses = findList(params, 'Чувства:');
    const languages = findList(params, 'Языки:');
    const cr = findOne(params, 'Опасность:');
    const source = findOne(params, 'Источник:');

    const additional = params
        .find('.subsection')
        .get()
        .map((elem) => {
            const title = $(elem).find('h3.subsection-title').text();
            const items = $(elem)
                .find('div p')
                .get()
                .map((subElem) => $(subElem).text())
                .filter((text) => !isEmptyString(text));

            return { title, items };
        });

    return {
        index,
        title,
        size,
        type,
        alignment,
        ac,
        hp,
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
    return element.find(`li:has(>strong:contains("${title}"))`).text().replace(title, '').trim();
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
