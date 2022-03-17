


//https://eodhistoricaldata.com/api/real-time/BTC-USD.CC?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json&s=ETH,LTC,ADA,DOT,BCH,XLM,DOGE,BNB,USDT,XMR
    // const MARKET_API = 'https://api.polygon.io/v2/aggs/grouped/locale/us/market/stocks/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';
    // const MARKET_API =  'https://api.polygon.io/v1/open-close/AAPL/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';

    const CRYPTO_API ='https://api.polygon.io/v2/aggs/grouped/locale/global/market/crypto/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';
    // const CRYPTO_API = 'https://api.polygon.io/v1/open-close/crypto/BTC/USD/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';
    const NEWS_API = 'https://eodhistoricaldata.com/api/news?api_token=62323211e2b454.72150400&t=AAPL.US&offset=0&limit=';
    const NEWS_API_MKTAUX = 'https://api.marketaux.com/v1/news/all?symbols=TSLA%2CAMZN%2CMSFT&filter_entities=true&language=en&api_token=ZaTmr6mrrox3LxBsnqE92JhHuk6bgjAYWUVgoyFR';
    const MARKET_API = ' https://eodhistoricaldata.com/api/real-time/AAPL.US?api_token=62323211e2b454.72150400&fmt=json&s=EUR.FOREX,MSFT,TSLA,UNH,GOOGL,AMZN,FB,V,UNH';


    //MARKET//
     fetch(MARKET_API)
        .then(data => {
            return data.json();
        })
        .then(data => {
            console.log(data);
        });

    let getStocks = () => {
        return fetch(MARKET_API)
            .then(resp => resp.json())
            .then(data => {
                // console.log(data);
                $('#load-market').empty();
                let market = data;

                for (let result of market) {
                    //cards
                    $('#load-market').append(`
                      <li  style="list-style-type: none; color: white; display:inline-block;">
                     <span class="mx-3"> ${result.code}</span>
                     <i class="fa-solid fa-arrow-up" style="color: #2EB82E"></i><span class="mx-2" style="color: white">${result.high} </span>
                     <i class="fa-solid fa-arrow-down-long" style="color: red"></i><span class="mx-2" style="color: white">${result.low}</span>
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
                console.log(data);
                $('#load-crypto').empty();
                let crypto = data.results;

                for (let result of crypto) {
                    //cards

                    $('#load-crypto').append(`
                      <li  style="list-style-type: none;  display:inline-block;">
                     <span class="mx-3"> ${result.T}</span>
                    
                     <i class="fa-solid fa-arrow-up" style="color: #2EB82E"><span class="mx-2">${result.h} </span>
                     <i class="fa-solid fa-arrow-down-long" style="color: red"><span class="mx-2">${result.c}</span> </li>`)
                 }
            })
    }

    getCrypto();



   //MARKET NEWS//


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
                let newsMarket = data;

                for (let property of newsMarket) {
                    //cards
                    $('#load-news').append(`
                    <div class="card" style="width: 30rem;">
                        <h3 class="newsTitle text-center">${property.title}</h3>
                        
                        <p class="newsDescription">${property.content}</p>

                       <a class="newsUrl">${property.link}</a>
                 </div>`)
                }
            })
    }

    getMarketNews();

// let getMarketNews2 = () => {
//     return fetch(NEWS_API_MKTAUX)
//         .then(resp => resp.json())
//         .then(data => {
//             console.log(data);
//             $('#load-news-2').empty();
//             let newsMkt = data.data;
//
//             for (let property of newsMkt) {
//                 //cards
//                 $('#load-news-2').append(`
//                     <div class="card" style="width: 30rem;">
//                         <h3 class="newsTitle text-center">${property.title}</h3>
//                         <img>${property.image_url}
//                         <p class="newsDescription">${property.description}</p>
//
//                        <a class="newsUrl">${property.url}</a>
//                  </div>`)
//             }
//         })
// }
//
// getMarketNews2();













