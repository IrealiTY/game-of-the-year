import express, {Request, Response} from 'express'

const infoController = (request: Request, response: Response) => {
    response.status(200)
    return response.json({hello: 'world'})
}
export const infoRouter = express.Router()
infoRouter.route('/').get(infoController)
