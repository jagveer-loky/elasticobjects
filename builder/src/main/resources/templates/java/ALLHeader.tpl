==>{JavaImportCall->.}.
/**
 * \
 ==>{JavaCommentCall->description|>}.
 *
 * @author \
 =>{author}.
 * @creationDate \
 =>{creationDate|>}.
 * @modificationDate \
 =>{/date|>}.
 */
public
==>{TemplateCall->., abstract eq true}| abstract=>{}. \
==>{TemplateCall->., shapeType eq INTERFACE}|interface \
=>{}.
==>{TemplateCall->., shapeType ne INTERFACE}|class \
=>{}.
=>{modelKey}.=>{/fileEnding}.
==>{TemplateCall->., superKey ex}| extends \
=>{superKey}.=>{}. \
==>{JavaImplementsCall->interfaces|>}. {