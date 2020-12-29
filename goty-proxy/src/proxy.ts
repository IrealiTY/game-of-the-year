import express from 'express'
import cors from 'cors'
import bodyParser from 'body-parser'
import {infoRouter} from './info'
import redis from 'redis'

const setupExpress = () => {
    const app = express()
    app.use(cors())
    app.use(
        bodyParser.urlencoded({
            extended: true
        })
    )
    app.use(bodyParser.json())
    const port = 3050;
    app.use('/info', infoRouter)
    app.listen(port, () => console.log(`Running GOTY Proxy on port ${port}!`))
}

const setupRedis = () => {
    const client = redis.createClient()
    client.set('hello', 'heya', redis.print)
    client.get('hello', redis.print)
}

setupExpress()
setupRedis()
