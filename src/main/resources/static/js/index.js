


//https://eodhistoricaldata.com/api/real-time/BTC-USD.CC?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json&s=ETH,LTC,ADA,DOT,BCH,XLM,DOGE,BNB,USDT,XMR
    // const MARKET_API = 'https://api.polygon.io/v2/aggs/grouped/locale/us/market/stocks/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';
    // const MARKET_API =  'https://api.polygon.io/v1/open-close/AAPL/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';


    const CRYPTO_API = 'https://api.polygon.io/v1/open-close/crypto/BTC/USD/2020-10-14?adjusted=true&apiKey=gHpHxDBVFlIGHibiBioo052yi8honmWu';
    const NEWS_API = ' https://eodhistoricaldata.com/api/news?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&s=AAPL.US&offset=0&limit=3';

    const MARKET_API = ' https://eodhistoricaldata.com/api/real-time/AAPL.US?api_token=62323211e2b454.72150400&fmt=json&s=VTI,EUR.FOREX,MSFT,TSLA,UNH,GOOGL,AMZN,FB,V,UNH';


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
                        <h3 class="newsTitle text-center">${property.title.toUpperCase()}</h3>
                        
                        <p class="newsDescription">${property.content}</p>

                       <a class="newsUrl">${property.link}</a>
                 </div>`)
                }
            })
    }

    getMarketNews();














