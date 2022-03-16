

    //  url for API
    const NEWS_API = ' https://api.marketaux.com/v1/news/all?symbols=TSLA,AMZN,MSFT&filter_entities=true&language=en&api_token=ZaTmr6mrrox3LxBsnqE92JhHuk6bgjAYWUVgoyFR' ;


    const MARKET_API = 'https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=WF0J3C09DVQXVCSK'

    // const CRYPTO_API = 'https://www.alphavantage.co/query?function=CRYPTO_INTRADAY&symbol=ETH&market=USD&interval=5min&apikey=WF0J3C09DVQXVCSK'
    const CRYPTO_API = 'https://api.polygon.io/v3/reference/tickers?active=true&sort=ticker&order=asc&limit=10&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';


    fetch(CRYPTO_API)
        .then(data => {
            return data.json();
        })
        .then(data => {
            console.log(data);
        });



/// market news// working on test page not working here



    fetch(NEWS_API)
        .then(data => {
            return data.json();
        })
        .then(data => {
            console.log(data);
        });

    // market news//

    let getMarketNews = () => {
        return fetch(NEWS_API)
            .then(resp => resp.json())
            .then(data => {
                console.log(data);
                $('#load-news').empty();
                let newsMarket = data.data;

                for (let property of newsMarket) {
                    //cards
                    $('#load-news').append(`<div>
                    <div class="card" style="width: 30rem;">
                        <h3 class="newsTitle text-center">${property.title.toUpperCase()}</h3>
                        <img id="newsPoster" src="${property.image_url}" class="card-img-top" alt="pic" style="width: 20rem;">
                        <div class="card-body body">
                        <p class="newsDescription">${property.description}</p>
                       
                       <a class="newsUrl">${property.url}</a>
                 </div>
                 </div>`)
                }
            })
    }

    getMarketNews();


    // CRYPTO//

    // fetch(CRYPTO_API)
    //     .then(data => {
    //         return data.json();
    //     })
    //     .then(data => {
    //         console.log(data);
    //     });



    let getCrypto = () => {
        return fetch(CRYPTO_API)
            .then(resp => resp.json())
            .then(data => {
                console.log(data);
                $('#load-market').empty();
                let market = data.results;

                for (let result of market) {
                    //cards
                    $('#load-market').append(`<div class="row" >
                    <div class="card" style="width: 15rem;">
                        <p class="newsTitle text-center">${result.ticker}</p>
                        <p class="newsDescription">${result.name}</p>
                        <p class="newsDescription">${result.primary_exchange}</p>
                        <p class="newsDescription">${result.type}</p>
                        <p class="newsDescription">${result.locale}</p>
                       
                     </div>
                 </div>`)
                }
            })
    }

    getCrypto();











