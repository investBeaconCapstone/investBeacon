




     let CRYPTO_API =("https://api.polygon.io/v2/aggs/grouped/locale/global/market/crypto/2020-10-14?adjusted=true&apiKey=" + POLYGON_API);
    let NEWS_API = ("https://eodhistoricaldata.com/api/news?api_token="+ EOD_API +"&s=AAPL.US&offset=0&limit=3");
    let NEWS_API_MKTAUX = ("https://api.marketaux.com/v1/news/all?symbols=TSLA%2CAMZN%2CMSFT&filter_entities=true&language=en&api_token=" + MARKETAUX_API);
    let MARKET_API = ("https://eodhistoricaldata.com/api/real-time/AAPL.US?api_token=" + EOD_API + "&fmt=json&s=EUR.FOREX,MSFT,TSLA,UNH,GOOGL,AMZN,FB,UNH");


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
            }).catch(err => console.error("This is your err:", err));
    }
    getStocks();




   // CRYPTO//
    fetch(CRYPTO_API)
        .then(data => {
            return data.json();
        })
        .then(data => {
            console.log(data);
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
                     <span class="mx-3" style="color: #FFFFFF"> ${result.T}</span>

                     <i class="fa-solid fa-arrow-up" style="color: #2EB82E"><span class="mx-2">${result.h} </span>
                     <i class="fa-solid fa-arrow-down-long" style="color: red"><span class="mx-2">${result.c}</span> </li>`)
                 }
            }).catch(err => console.error("This is your err:"));
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

    // market news//

    let getMarketNews = () => {
        return fetch(NEWS_API)
            .then(resp => resp.json())
            .then(data => {
                console.log(data);
                $('#load-news').empty();
                let newsMarket = data;

                for (let result of newsMarket) {
                    //cards
                    $('#load-news').append(`
                    <div class="col-md-4">
                         <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative" style="width: 30rem; height: 30rem; border: none; color:black;">
                            <div class="col p-4 d-flex flex-column position-static">
                                <strong class="d-inline-block mb-2 text-primary"></strong>
                                    <h3 class="mb-0">${result.title.toUpperCase()}</h3>
                                   <div class="mb-1">${result.date}</div>
                                    <p class="card-text mb-auto">${result.content}</p>
                                    <a href="${result.link}" class="stretched-link">Continue reading</a>
                                </div>
                            </div>                      
                         </div>`)
                }
            }).catch(err => console.error("This is your err:", err));
    }

    getMarketNews();

let getMarketNews2 = () => {
    return fetch(NEWS_API_MKTAUX)
        .then(resp => resp.json())
        .then(data => {
            console.log(data);
            $('#load-news-2').empty();
            let newsMkt = data.data;

            for (let property of newsMkt) {
                //cards
                $('#load-news-2').append(`
                <div class="col-md-4">
                         <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative" style="width: 30rem; height: 30rem; border: none; color:black;">
                            <div class="col p-4 d-flex flex-column position-static">
                                <strong class="d-inline-block mb-2 text-primary"></strong>
                                    <h3 class="mb-0">${property.title}</h3>
                                   <div class="mb-1">${property.date}/div>
                                    <p class="card-text mb-auto">${property.description}</p>
                                    <a href="${property.url}" class="stretched-link">Continue reading</a>
                                </div>
                                 <div class="col-auto d-none d-lg-block">
          <img class="bd-placeholder-img" width="200" height="250"  role="img" aria-label="Placeholder: Thumbnail" src="${property.image_url}"><rect width="100%" height="100%" /></img>

        </div>
                            </div>
                         </div>
`)
            }
        }).catch(err => console.error("This is your err:", err));
}

getMarketNews2();













