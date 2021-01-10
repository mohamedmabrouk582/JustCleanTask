package com.example.data.utils

import com.example.data.entities.CommentEntity
import com.example.data.entities.FavPostsOffline
import com.example.data.entities.PostEntity
import com.example.domain.models.Comment
import com.example.domain.models.Post

fun Post.toEntity() : PostEntity =
    PostEntity(id, userId, title, body)

fun Comment.toEntity() : CommentEntity =
    CommentEntity(id, postId, name, email, body)

fun ArrayList<Post>.toEntity() : ArrayList<PostEntity> =
    ArrayList(map { it.toEntity() }.toList())

@JvmName("toEntityComment")
fun ArrayList<Comment>.toEntity() : ArrayList<CommentEntity> =
    ArrayList(map { it.toEntity() }.toList())

fun PostEntity.toOffline() : FavPostsOffline =
    FavPostsOffline(id, userId, title, body)

fun FavPostsOffline.toDomain() : Post =
    Post(id, userId, title, body)

fun List<FavPostsOffline>.toDomain() : ArrayList<Post> = ArrayList(map { it.toDomain() })


fun PostEntity.toDomain() : Post =
    Post(id, userId, title, body)

fun CommentEntity.toDomain() : Comment =
    Comment(id, postId, name, email, body)

fun ArrayList<PostEntity>.toDomain() : ArrayList<Post> =
    ArrayList(map { it.toDomain() }.toList())

@JvmName("toDomainCommentEntity")
fun ArrayList<CommentEntity>.toDomain() : ArrayList<Comment> =
    ArrayList(map { it.toDomain() }.toList())

fun itemsAsFav(fromApi:ArrayList<PostEntity>,fromDb:List<PostEntity>) =
    fromDb.map { entity ->
        fromApi.forEach {
            if (entity.id==it.id){
                it.isFav=true
            }
        }
    }
