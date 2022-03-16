



    // const MARKET_API = 'https://api.polygon.io/v2/aggs/grouped/locale/us/market/stocks/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';
    const MARKET_API =  'https://api.polygon.io/v1/open-close/AAPL/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';
    const CRYPTO_API = 'https://api.polygon.io/v1/open-close/crypto/BTC/USD/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';
    // const NEWS_API = ' https://api.marketaux.com/v1/news/all?symbols=TSLA,AMZN,MSFT&filter_entities=true&language=en&api_token=' + MARKETAUX_API;



    //MARKET//
     fetch(MARKET_API)
        .then(data => {
            return data.json();
        })
        .then(data => {
            // console.log(data);
        });

    let getStocks = () => {
        return fetch(MARKET_API)
            .then(resp => resp.json())
            .then(data => {
                // console.log(data);
                $('#load-market').empty();
                let market = data.results;

                for (let result of market) {
                    //cards
                    $('#load-market').append(`
                      <li  style="list-style-type: none; color: white">
                     <span class="mx-3"> ${result.T}</span>
                     <i class="fa-solid fa-arrow-up" style="color: #2EB82E"></i><span class="mx-2" style="color: white">${result.h} </span>
                     <i class="fa-solid fa-arrow-down-long" style="color: red"></i><span class="mx-2" style="color: white">${result.l}</span>
                     </li>`)
                }
            })
    }
    getStocks();




   // CRYPTO//
    fetch(CRYPTO_API)
        .then(data => {
            return data.json();
        })
        .then(data => {
            // console.log(data);
        });

    let getCrypto = () => {
        return fetch(CRYPTO_API)
            .then(resp => resp.json())
            .then(data => {
                // console.log(data);
                $('#load-crypto').empty();
                let crypto = data;

                // for (let result of crypto) {
                //     //cards
                    $('#load-crypto').append(`
                      <li>
                     <span> ${crypto.symbol}</span>
                     </li>
                     <span>${crypto.open} </span>
                     <span>${crypto.close}</span>
                      `)
                 // }
            })
    }

    getCrypto();



   //MARKET NEWS//




    // fetch(NEWS_API)
    //     .then(data => {
    //         return data.json();
    //     })
    //     .then(data => {
    //         console.log(data);
    //     });
    //
    // // market news//
    //
    // let getMarketNews = () => {
    //     return fetch(NEWS_API)
    //         .then(resp => resp.json())
    //         .then(data => {
    //             console.log(data);
    //             $('#load-news').empty();
    //             let newsMarket = data.data;
    //
    //             for (let property of newsMarket) {
    //                 //cards
    //                 $('#load-news').append(`<div>
    //                 <div class="card" style="width: 30rem;">
    //                     <h3 class="newsTitle text-center">${property.title.toUpperCase()}</h3>
    //                     <img id="newsPoster" src="${property.image_url}" class="card-img-top" alt="pic" style="width: 20rem;">
    //                     <div class="card-body body">
    //                     <p class="newsDescription">${property.description}</p>
    //
    //                    <a class="newsUrl">${property.url}</a>
    //              </div>
    //              </div>`)
    //             }
    //         })
    // }
    //
    // getMarketNews();














